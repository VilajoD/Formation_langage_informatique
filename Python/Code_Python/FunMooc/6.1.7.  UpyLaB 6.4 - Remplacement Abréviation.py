"""
fonction substitue(message, abreviation) qui renvoie une copie de la chaîne de caractères message
dans laquelle les mots qui figurent parmi les clés du dictionnaire abreviation sont remplacés par
leur signification (valeur).
"""

def substitue(message, abreviation):
    message_decode = message
    for abrev in abreviation:
        if abrev in message_decode:
            message_decode = message_decode.replace(abrev,abreviation[abrev])

    return(message_decode)

message = 'C. N. cpt 2 to inf'
abrev ={'C.' : 'Chuck',
                                 'N.' : 'Norris',
                                 'cpt' : 'counted',
                                 '2' : 'two times',
                                 'inf' : 'infinity'}

print(substitue(message,abrev))