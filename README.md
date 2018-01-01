# AvrLibraryTemplate
AvrLibrary template project generator for Atmel Studio 7. This is an eclipse gradle project. If you only wat the executable, just check out the exec directory. 

![Screenshot](/images/screenshot.png)

Parameters:
* AvrDude Path: The path to your [AVR Dude](http://savannah.nongnu.org/projects/avrdude) installation
* AvrDude Config file: The AvrDude config file used as command line argument for programming
* CPU: the CPU used
* Programming algorithm: The AVR Dude Programming algorithm, typically arduino for ATMega 328, wiring for ATMega 256X, avr109for ATMega32u4
* Com Port: The Comport used to communicate with the CPU
* Baudrate: The serial baud rate
* Workspace Directory: The desired Atmel Studio workspace directory
* Library Path: The path to the [AVR Library](https://github.com/saarbastler/AvrLibrary.git), check out in a directory next to the workspace
* Assembly name: THe Atmel Studio workspace directory
* CPU Frequency: The CPU frequency

Clicking on Start will create the complete skeleton Atmel Studio 7 project.
  