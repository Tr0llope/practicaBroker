En primer lugar modificar las direcciones de los registros RMI en Broker.java, ServerAImpl.java ServerBImpl.java y ClientC.java. Después se compilar todo con:
        $ javac *.java
A continuación se debe lanzar en cada máquina el registro RMI con la dirección y puertos correspondientes. Por ejemplo:
        $ rmiregistry 320001 &

Luego se ejecuta el broker en su correspondiente máquina:
        $ java BrokerImpl

Después se ejecutan los servidores:
        $ java ServerAImpl
        $ java ServerBImpl

Y finalmente se puede ejecutar el cliente:
        $ java ClientC

Se podría ejecutar el cliente antes que los servidores pero no habría registrado ningún servicio.
