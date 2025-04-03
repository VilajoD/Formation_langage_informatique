"""
fonction longueur(*points) qui reçoit en paramètres un nombre arbitraire de points (tuples de deux composantes),
et retourne la longueur de la ligne brisée correspondante.

Cette longueur se calcule en additionnant les longueurs des segments formés par deux points consécutifs.
"""

def longueur(*points):

    def distance_points(x, y):
        dist = ((x[0] - y[0]) ** 2 + (x[1] - y[1]) ** 2) ** (1 / 2)
        return (dist)

    distance = 0
    for i in range(len(points)-1):
        distance += distance_points(points[i],points[i+1])

    return(distance)

res = longueur((0.5, 1.0), (2.0, 1.0), (2.5, -0.5), (-1.5, -1.0))

print(res)