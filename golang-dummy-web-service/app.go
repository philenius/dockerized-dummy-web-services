package main

import (
    "fmt"
    "log"
    "net/http"

    "os"

    "github.com/gorilla/mux"
)

func main() {

    port := getEnv("SERVER_PORT", "8081")

    router := mux.NewRouter()
    router.HandleFunc("/", homeHandler)
    http.Handle("/", router)
    log.Println("listening on :" + port)
    http.ListenAndServe(":" + port, router)
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
    writer.Write([]byte(fmt.Sprintf("{ \"hostname\": \"%s\", \"MY_ENV_VAR\": \"%s\" }", hostname, os.Getenv("MY_ENV_VAR"))))
}
