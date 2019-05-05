package main

import (
	"fmt"
	"log"
	"net/http"
	"time"

	"os"

	"github.com/gorilla/mux"
)

var (
	shutdownOngoing  = false
	remainingSeconds = 10
)

func main() {

	port := getEnv("SERVER_PORT", "8081")

	router := mux.NewRouter()
	router.HandleFunc("/", homeHandler)
	router.HandleFunc("/health", healthHandler)
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
	log.Println("received request", acc, enc, lang, cache, con, host, pragma, upgradeSSL, userAgent)

	hostname, err := os.Hostname()
	if err != nil {
		log.Println("failed to retrieve hostname", err.Error())
	}
	writer.Header().Set("Content-Type", "application/json")
	writer.Write([]byte(fmt.Sprintf("{ \"hostname\": \"%s\", \"MY_ENV_VAR\": \"%s\" }\n", hostname, os.Getenv("MY_ENV_VAR"))))
}

func healthHandler(writer http.ResponseWriter, request *http.Request) {
	writer.WriteHeader(http.StatusOK)
	writer.Write([]byte("OK"))
}

func shutdownHandler(writer http.ResponseWriter, request *http.Request) {
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
