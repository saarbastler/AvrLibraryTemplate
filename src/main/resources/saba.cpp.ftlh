#include "saba.h"

// the Usart driver, using 57600 Baud
SABA::USART0 usart(57600);

// the OStream putch method redirects output to the Usart
void putch(uint8_t c)
{
  usart.putch(c);
}

// The application output stream, similar to std::cout. 
// It will use Usart for output
SABA::OStream <&putch> out;

// The command line will use out as input. It will be filled within the main loop
SABA::CmdLine<uint8_t,CMD_LINE_SIZE,&putch,SABA::OStream,execute> cmdline;

// The Debug Monitor defines a few commands accessible by serial port
typedef SABA::Monitor<uint8_t,CMD_LINE_SIZE,SABA::OStream,&putch> DebugMonitor;

// The system wide ticker, has to be incremented periodically
volatile uint16_t SABA::Timing::ticker asm("Timingticker");

bool execute(char c)
{
  switch(c)
  {
    case 'A':
      return DebugMonitor::adc(cmdline);
    case 'P':
      return DebugMonitor::ports(cmdline);
    case 'S':
      return DebugMonitor::spi(cmdline);

    default:
      return applicationCommand(c);
  }
}

int main(void)
{
  initializeApplication();
  sei();

  for(;;)
  {
    if(usart.receiverComplete())
      cmdline.appendChar(usart.getch());

    cyclic();
  }
}
