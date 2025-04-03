"""
Joao vient d’arriver dans notre pays depuis le Portugal. Il a encore du mal avec la langue française.
Malgré ses efforts considérables, il fait une faute d’orthographe quasi à chaque mot. Son souci est qu’il n’arrive
pas toujours à écrire un mot correctement sans se tromper à une lettre près. Ainsi pour écrire « bonjour »,
il peut écrire « binjour ». Pour remédier à ce problème, Joao utilise un correcteur orthographique.
Malheureusement, Joao a un examen aujourd’hui et il a oublié son petit correcteur.

Afin de l’aider, nous vous demandons d’écrire une fonction correcteur(mot, liste_mots) où mot est le mot
que Joao écrit et liste_mots est une liste qui contient les mots (ayant la bonne orthographe) que Joao
est susceptible d’utiliser.

Cette fonction doit retourner le mot dont l’orthographe a été corrigée.
"""

def correcteur(mot, liste_mots):

    def distance_mots(mot_1, mot_2):
        """
        Donne la distance entre 2 mots
        :param mot_1: 1er mot
        :param mot_2: 2nd mot
        :return: la distance en int
        """
        distance = 0
        i = 0
        #for i in range(len(mot_1)):
        while i < len(mot_1) and i < len(mot_2):
            if mot_1[i] != mot_2[i]:
                distance += 1
            i += 1
        return (distance)


    distance_mini = len(mot)
    mot_corrige = ''
    for i in range(len(liste_mots)):
        distance = distance_mots(mot,liste_mots[i])
        if distance < distance_mini :
            distance_mini = distance
            mot_corrige = liste_mots[i]

    return(mot_corrige)


print(correcteur("Meric", ["chien", "chat", "train", "voiture", "bonjour", "merci"]))