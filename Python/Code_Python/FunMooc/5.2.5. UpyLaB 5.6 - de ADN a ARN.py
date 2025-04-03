"""
fonction transcription_arn(brin_codant) qui reçoit une chaîne de caractères en paramètre, correspondant à
un brin codant d'ADN, et qui retourne la chaîne de caractère représentant le brin d' ARN correspondant.".
"""

def transcription_arn(brin_codant):
    seq_ARN = ""
    for c in brin_codant:
        if c == 'T':
            seq_ARN += "U"
        else :
            seq_ARN += c

    return (seq_ARN)


cas = 'AGTCTTACCGATCCAT'
print(transcription_arn(cas))