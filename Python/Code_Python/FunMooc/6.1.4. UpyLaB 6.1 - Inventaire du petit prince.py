"""
fonction inventaire(offres, objets) où :
offres est un dictionnaire contenant, comme clés, les objets proposés par les amis du Petit Prince,
et comme valeurs associées, le nom de l'ami proposant cet objet,

objets est une liste contenant tous les objets dont a besoin le Petit Prince.

La fonction retourne l'ensemble des amis chez qui il lui faut se rendre pour sa récolte.
"""

def inventaire(offres, objets):
 #   amis = set()
 #   for proposition in offres:
 #       print(proposition)
 #       if proposition in objets:
 #           amis.add(offres[proposition])
 #   return amis
    return({offres[proposition] for proposition in offres if proposition in objets})




offre = {"lit" : "Antoine", "bibliothèque" : "Sébastien", "chaise" : "Isabelle",
            "livre 'Le vieil homme et la mer'" : "Ernest", "sac de bonbons" : "Thierry",
            "smartphone" : "Ted", "table" : "Sophie"}

objet = ["sac de bonbons", "table", "chaise", "lit", "livre 'Le vieil homme et la mer'"]

print(inventaire(offre,objet))