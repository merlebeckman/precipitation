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
        capability "Switch Level"
        capability "Sensor"
        capability "Health Check"
        attribute "Daily Precipitation", "number"
        command "setDailyPrecipitation", ["number"]
    }

    // UI tile definitions
    tiles {
        valueTile("dailyprecipitation", "device.dailyprecipitation", width: 2, height: 2) {
            state("val", label:'Daily Precipitation: ${currentValue}',  backgroundColor: "#e86d13", defaultState: true)
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
    sendEvent(name: "DeviceWatch-DeviceStatus", value: "online")
    sendEvent(name: "healthStatus", value: "online")
    //sendEvent(name: "DeviceWatch-Enroll", value: [protocol: "cloud", scheme:"untracked"].encodeAsJson(), displayed: false)
    if (!device.currentState("dailyprecipitation")) {
        setDailyPrecipitation(getDailyPrecipitation())
    }
    //sendEvent(name: "dailyprecipitation", value: 22.01)
}

def setLevel(value) {
    setDailyPrecipitation(value)
}

def setDailyPrecipitation(value) {
    sendEvent(name:"dailyprecipitation", value: value)
}


private getDailyPrecipitation() {
    def ts = device.currentState("dailyprecipitation")
    Float value = ts ? ts.floatValue : 21.01
    return value
}