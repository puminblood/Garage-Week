import serial

# Remplacez 'COM_PORT' par le port COM attribué à votre module Bluetooth
ser = serial.Serial('COM9', 115200)

try:
    while True:
        if ser.in_waiting > 0:
            line = ser.readline().decode('utf-8').rstrip()
            print("Received:", line)
except KeyboardInterrupt:
    print("Program interrupted")
finally:
    ser.close()
    print("Connection closed")
