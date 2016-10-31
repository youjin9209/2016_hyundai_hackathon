#include <SoftwareSerial.h>
SoftwareSerial xbee(2, 3); 
 
void setup() {
xbee.begin(9600); 
}
void loop() {
xbee.print('A');
delay(1000);
}

