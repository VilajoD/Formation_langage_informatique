from math import pi, cos, sin

long = float(input())

x1 = cos(1*(pi/3))
y1 = sin(1*(pi/3))

x2 = cos(2*(pi/3))
y2 = sin(2*(pi/3))

x3 = cos(3*(pi/3))
y3 = sin(3*(pi/3))

x4 = cos(4*(pi/3))
y4 = sin(4*(pi/3))

x5 = cos(5*(pi/3))
y5 = sin(5*(pi/3))

print(long, 0.0)
print(long * x1 , long * y1)
print(long * x2 , long * y2)
print(long * x3 , long * y3)
print(long * x4 , long * y4)
print(long * x5 , long * y5)