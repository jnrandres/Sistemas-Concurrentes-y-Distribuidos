
import socket                
import random  

s = socket.socket()          
  
port = 2222                
#172.31.46.43 | 3.19.82.156

s.connect(('3.19.82.156', port)) 


id = '0'
numero = 'nn'

while numero != 'salir':

    numero = input("Numero: ")
    
    message_to_send = ("p:"+str(id)+":"+numero).encode('UTF-8')
    s.send( len(message_to_send).to_bytes(2, byteorder='big') )
    s.send( message_to_send )

    data = s.recv(1024)
    string = str(data.decode('UTF-8'))
    
    partes = string.split("::")
    for parte in partes:
        if len(parte)>0 :
            print("Productor ["+id+"] y el mensaje del servidor: "+parte)


s.close()   