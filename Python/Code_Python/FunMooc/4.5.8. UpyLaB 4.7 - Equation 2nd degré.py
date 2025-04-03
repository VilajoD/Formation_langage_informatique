"""
Fonction rac_eq_2nd_deg(a, b, c) qui reçoit trois paramètres de type float correspondant
aux trois coefficients de l’équation du second degré ax^2 + bx + c = 0, avec a différent de 0,
et qui renvoie la ou les solutions s’il y en a, sous forme d’un tuple.
"""

def rac_eq_2nd_deg(a, b, c):

    delta=b**2-(4*a*c)

    if delta == 0:
        res = ((-b)/2*a,)

    elif delta > 0:
        res = (min((-b + delta**0.5)/(2*a),(-b - delta**0.5)/(2*a)),max((-b + delta**0.5)/(2*a),(-b - delta**0.5)/(2*a)))
        #res = ( (-b - delta ** 0.5) / (2 * a), (-b + delta ** 0.5) / (2 * a))

    else:
        res=()

    return(res)


print(rac_eq_2nd_deg(-1.5,4.5,4.0))
