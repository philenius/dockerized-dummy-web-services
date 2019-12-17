package main

import (
	"encoding/json"
	"fmt"
	"html/template"
	"log"
	"net/http"
	"strconv"
	"time"

	"os"

	"github.com/gorilla/mux"
)

var (
	healthy 		 = true
	port			 = "8081"
	shutdownOngoing  = false
	remainingSeconds = 10
)

func main() {

	port = getEnv("SERVER_PORT", "8081")

	router := mux.NewRouter()
	router.HandleFunc("/", homeHandler)
	router.HandleFunc("/info", infoHandler)
	router.HandleFunc("/health", healthHandler)
	router.HandleFunc("/health/{healthy}", setHealthHandler)
	router.HandleFunc("/shutdown", shutdownHandler)
	http.Handle("/", router)
	log.Println("listening on :" + port)
	http.ListenAndServe(":"+port, router)
}

func getEnv(key string, fallback string) string {
	if value, ok := os.LookupEnv(key); ok {
		return value
	}
	return fallback
}

func homeHandler(writer http.ResponseWriter, request *http.Request) {
	tmpl := template.Must(template.ParseFiles("layout.html"))
	tmpl.Execute(writer, nil)
}

func setHealthHandler(writer http.ResponseWriter, request *http.Request) {
	vars := mux.Vars(request)
	healthQueryParam, err := strconv.ParseBool(vars["healthy"])
	if err != nil {
		writer.WriteHeader(http.StatusBadRequest)
		writer.Write([]byte("BAD REQUEST"))
		return	
	}

	healthy = healthQueryParam
	log.Printf("GET /healthy/%t", healthy)

	writer.WriteHeader(http.StatusOK)
	writer.Write([]byte("OK"))
}

func infoHandler(writer http.ResponseWriter, request *http.Request){
	headerMap := request.Header
	acc := headerMap["Accept"]
	enc := headerMap["Accept-Encoding"]
	lang := headerMap["Accept-Language"]
	cache := headerMap["Cache-Control"]
	con := headerMap["Connection"]
	host := headerMap["Host"]
	pragma := headerMap["Pragma"]
	userAgent := headerMap["User-Agent"]
	upgradeSSL := headerMap["Upgrade-Insecure-Requests"]
	log.Println("GET /info\t\t", acc, enc, lang, cache, con, host, pragma, upgradeSSL, userAgent)

	hostname, err := os.Hostname()
	if err != nil {
		log.Println("failed to retrieve hostname", err.Error())
	}
	headerMapJSON, err := json.Marshal(headerMap)
	writer.Header().Set("Content-Type", "application/json")
	writer.Write([]byte(fmt.Sprintf("{ \"hostname\": \"%s\", \"SERVER_PORT\": %s, \"MY_ENV_VAR\": \"%s\", \"headerMap\": %s }\n", hostname, port, os.Getenv("MY_ENV_VAR"), headerMapJSON)))
}

func healthHandler(writer http.ResponseWriter, request *http.Request) {
	log.Println("GET /health\t\thealthy =", healthy)

	writer.Header().Add("Content-Type", "application/json")
	if healthy {
		writer.WriteHeader(http.StatusOK)
		writer.Write([]byte("{ \"healthy\": true }"))
		return	
	}
	writer.WriteHeader(http.StatusInternalServerError)
	writer.Write([]byte("{ \"healthy\": false }"))
}

func shutdownHandler(writer http.ResponseWriter, request *http.Request) {
	log.Println("GET /shutdown")
	writer.WriteHeader(http.StatusOK)

	if shutdownOngoing {
		writer.Write([]byte(fmt.Sprintf("shutting down in %d seconds", remainingSeconds)))
		return
	}

	shutdownOngoing = true

	log.Println(fmt.Sprintf("shutting down in %d seconds", remainingSeconds))
	writer.Write([]byte(fmt.Sprintf("shutting down in %d seconds", remainingSeconds)))

	go func() {
		ticker := time.NewTicker(time.Second)
		for range ticker.C {
			if remainingSeconds == 0 {
				ticker.Stop()
				log.Println("shutting down")
				os.Exit(1)
			}
			remainingSeconds--
			log.Println(fmt.Sprintf("shutting down in %d seconds", remainingSeconds))
		}
	}()
}
