
import socket                
import random  

s = socket.socket()          
  
port = 2222                
#172.31.46.43 | 3.19.82.156
s.connect(('3.19.82.156', port)) 


dato = 'nn'

while dato != 'salir':

    dato = input("Dato: ")
    message_to_send = ("p:"+dato).encode('UTF-8')
    s.send( len(message_to_send).to_bytes(2, byteorder='big') )
    s.send( message_to_send )

s.close()   

 