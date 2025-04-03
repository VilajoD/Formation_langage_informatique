"""
Fonction store_email(liste_mails) qui reçoit en paramètre une liste d’adresses e-mail et
qui renvoie un dictionnaire avec comme clés les domaines des adresses e-mail et comme valeurs
les listes d’utilisateurs correspondantes, triées par ordre croissant (UTF-8).
"""

def store_email(liste_mails):
    res ={}
    for mail in liste_mails:
        element = mail.split('@')
        if element[1] in res :
            res[element[1]].append(element[0])
            res[element[1]].sort()
        else :
            res[element[1]] = [element[0]]

    return(res)

mail = ['ludo@prof.ur', 'andre.colon@stud.ulb', 'thierry@profs.ulb', 'sébastien@prof.ur', 'eric.ramzi@stud.ur', 'bernard@profs.ulb', 'jean@profs.ulb']
print(store_email(mail))

print("{'profs.ulb': ['bernard', 'jean', 'thierry'], 'stud.ulb': ['andre.colon'], 'prof.ur': ['ludo', 'sébastien'], 'stud.ur': ['eric.ramzi']}")

