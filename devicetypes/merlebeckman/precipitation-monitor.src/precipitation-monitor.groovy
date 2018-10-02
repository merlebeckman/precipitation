/**
 *  Copyright 2018 Jim Beckman
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 */
metadata {
    definition (name: "Precipitation Monitor", namespace: "merlebeckman", author: "Jim Beckman") {
        capability "Polling"
        attribute "Daily Precipitation", "number"
        command "setDailyPrecipitation", ["number"]  
        attribute "Yesterday Precipitation", "number"
        command "setYesterdayPrecipitation", ["number"]          
    }

    // UI tile definitions
    tiles (scale: 2) {
        valueTile("dailyprecipitation", "device.dailyprecipitation", width: 6, height: 6) {
            state("val", label:'Today: ${currentValue}',  backgroundColor: "#00a0dc", defaultState: true)
        }
        valueTile("yesterdayprecipitation", "device.yesterdayprecipitation", width: 6, height: 2) {
            state("val", label:'Yesterday: ${currentValue}')
        }
        //main "dailyprecipitation"
        //details("dailyprecipitation")
    }
}

// Parse incoming device messages to generate events
def parse(String description) {
    def pair = description.split(":")
    createEvent(name: pair[0].trim(), value: pair[1].trim())
}

def installed() {
    initialize()
}

def updated() {
    initialize()
}

def initialize() {
    if (!device.currentState("dailyprecipitation")) {
        setDailyPrecipitation(getDailyPrecipitation())
    }
    if (!device.currentState("yesterdayprecipitation")) {
        setYesterdayPrecipitation(getYesterdayPrecipitation())
    }
}

def setDailyPrecipitation(value) {
    sendEvent(name:"dailyprecipitation", value: value)
}

def setYesterdayPrecipitation(value) {
    sendEvent(name:"yesterdayprecipitation", value: value)
}

private getDailyPrecipitation() {
    def ts = device.currentState("dailyprecipitation")
    Float value = ts ? ts.floatValue : 0.00
    return value
}

private getYesterdayPrecipitation() {
    def ts = device.currentState("yesterdayprecipitation")
    Float value = ts ? ts.floatValue : 0.00
    return value
}