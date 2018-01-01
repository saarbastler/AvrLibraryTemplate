package de.saarbastler.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="devices")
public class Device
{
  @XmlElement
  private String cpu;
  @XmlElement
  private String avrdevice;
  @XmlElement
  private String mcu;

  
  public Device()
  {
    super();
  }

  public Device(String cpu, String avrdevice, String mcu)
  {
    super();
    this.cpu = cpu;
    this.avrdevice = avrdevice;
    this.mcu = mcu;
  }

  public String getCpu()
  {
    return cpu;
  }

  public String getAvrdevice()
  {
    return avrdevice;
  }

  public String getMcu()
  {
    return mcu;
  }
}