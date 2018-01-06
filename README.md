# AvrLibraryTemplate
AvrLibrary template project generator for Atmel Studio 7. This is an eclipse gradle project. If you only want the executable, just check out the exec directory. 

The General Tab:
![Screenshot](/images/tab1.png)
* Library Path: The path to the [AVR Library](https://github.com/saarbastler/AvrLibrary.git), check out in a directory next to the workspace
* write configuration to project: Write a xml file to the project containing the entered data

The CPU tab:
![Screenshot](/images/tab2.png)
* CPU: the CPU used
* CPU Frequency: The CPU frequency

The Serial Port Tab:
![Screenshot](/images/tab3.png)
* Com Port: The Comport used to communicate with the CPU
* Baudrate: The serial baud rate
* Write terminal.cmd file: Write a batch file starting the terminal program [TeraTerm](https://ttssh2.osdn.jp/index.html.en) with the configured Baudrate and port

The AvrDude Tab:
![Screenshot](/images/tab4.png)
* AvrDude Path: The path to your [AVR Dude](http://savannah.nongnu.org/projects/avrdude) installation
* AvrDude Config file: The AvrDude config file used as command line argument for programming
* Programming algorithm: The AVR Dude Programming algorithm, typically arduino for ATMega 328, wiring for ATMega 256X, avr109for ATMega32u4
* Write program.cmd file: Write a batch file calling AVRDude to program the device 

The Project Tab:
![Screenshot](/images/tab5.png)
* Workspace Directory: The desired Atmel Studio workspace directory
* Assembly name: The Atmel Studio workspace directory

Clicking on "write Template" will create the complete skeleton Atmel Studio 7 project.
  