def histogram(s):
    """Renvoie le dictionnaire des lettres dans s
       avec leur fréquence."""
    d = {}
    for c in s:
        d[c] = d.get(c,0) + 1
    return d

def get_keys(d, value):
    return([cle for cle,valeur in d.items() if valeur == value])

d = histogram('evenement')
print(d)
res={}
for valeur in set(d.values()):
    res[valeur] = get_keys(d,valeur)
print(res)