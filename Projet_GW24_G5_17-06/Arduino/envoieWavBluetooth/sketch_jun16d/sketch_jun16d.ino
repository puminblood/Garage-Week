#include <SPI.h>
#include <SD.h>
#include <SoftwareSerial.h>

File myFile;
SoftwareSerial myBluetooth(2, 3); // RX, TX

void setup() {
  Serial.begin(9600);
  myBluetooth.begin(115200);
  Serial.print("Initializing SD card...");
  if (!SD.begin(4)) {
    Serial.println("initialization failed!");
    return;
  }
  Serial.println("initialization done.");

  myFile = SD.open("test.wav");
  if (myFile) {
    Serial.println("test.wav:");
    // read from the file until there's nothing else in it:
    while (myFile.available()) {
      myBluetooth.write(myFile.read()); // Send data to Bluetooth
    }
    myFile.close();
  } else {
    // if the file didn't open, print an error:
    Serial.println("error opening test.wav");
  }
}

void loop() {
  // nothing happens after setup
}
