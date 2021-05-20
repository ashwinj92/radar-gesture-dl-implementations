#include <Arduino.h>

void setup() // This code is executed once for the initial setup
{
  pinMode(LED1, OUTPUT); // Set our LED as an output, so it can be controlled

}

void loop() // The following code is repeatedly executed
{
  digitalWrite(LED1, HIGH);  // Turn on the LED
  delay(500);                // Wait 0.5 seconds
  digitalWrite(LED1, LOW);   // Turn off the LED
  delay(500);                // Wait 0.5 seconds
}
