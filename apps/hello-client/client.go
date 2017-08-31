package main

import (
	"flag"
	"fmt"
	"github.com/retrievercommunications/http"
	"os"
	"time"
)

var (
	pollPeriod uint // in milliseconds
	url        string
)

func init() {
	flag.UintVar(&pollPeriod, "p", 2000, "the number of milliseconds between requests")
	flag.Parse()

	url = flag.Arg(0)

	if url == "" {
		fmt.Println("Missing url argument")
		os.Exit(1)
	}
}

func main() {
	client := &http.Client{}

	for {
		// Send a request to the given URL
		resp, err := client.Get(http.NewGetRequest(url))
		if err != nil {
			fmt.Println(err)
			os.Exit(1)
		}

		// Extract the relevant data from the response and print it
		server := resp.Header.Get("Server")
		payload := string(resp.Payload)
		fmt.Println(server + " - " + payload)

		// Wait for the poll period to expire before sending the next request
		time.Sleep(time.Duration(pollPeriod) * time.Millisecond)
	}
}
