"""
fonction distance_points() qui reçoit en paramètres deux tuples de deux composantes représentant les coordonnées
de deux points et qui retourne la distance euclidienne séparant ces deux points.
"""

def distance_points(x,y):
    dist=((x[0]-y[0])**2+(x[1]-y[1])**2)**(1/2)
    return(dist)

res=distance_points((-1.0, 0.5), (2.0, 1.0))

print(res)