#include <SoftwareSerial.h>
SoftwareSerial xbee(2, 3); 

int  led = 13;
 
void setup() {
pinMode(led, OUTPUT);   
Serial.begin(9600);
xbee.begin(9600); 
}
void loop() {
if(xbee.available()){
  char data = xbee.read();
  if(data == 'A') 
    Serial.print("Zigbee test OK!"); 
      // LED를 ON 합니다.
  digitalWrite(led, HIGH);
  // 1초간 대기합니다.
  delay(1000);        
  // LED를 OFF 합니다.
  digitalWrite(led,LOW);
  // 1초간 대기합니다.
  delay(1000);    
  }
}

