import numpy as np

#On va generer des données aleatoires
from sklearn.datasets import make_regression
import matplotlib.pyplot as plt

#--------------------------------------------------------------
# Création des matrice. X, y et theta
#--------------------------------------------------------------

#On va créer des data qui vont nous produire 2 tableau (x et y) --> 100 ligne 1 feature et un bruit de 10
x, y = make_regression(n_samples=100, n_features=1, noise=10)

#Création d un graph avec x en abscisse et y en ordonné
plt.scatter(x,y)

#Afficher le graph
#plt.show()

#Vérifion nos matrice
print(x.shape)
print(y.shape)
print(' ')

#En utilisant make_regression, il manque toujours une info sur le y
#Donc on va le redimensionné pour qu'il soit en 100,1 comme pour x
y = y.reshape (y.shape[0],1)
print(y.shape)
print(' ')

#Il nous faut créer la matrice X. Qui contient toutes les ligne de x + un colonne de biais ( la colonne toujours a 1)
X = np.hstack((x,np.ones(x.shape)))
print(X)
print(' ')

#Il nous faut créer le vecteur THETA. On l'initialise avec un nombre a et b au hasard
theta = np.random.randn(2,1)
print(theta)
print(' ')

#--------------------------------------------------------------
# Création du modele
#--------------------------------------------------------------

#On créer une fonction --> Parce que c'est plus classe
def model (X, theta):
    return X.dot(theta)

# on créer notre model, pour ca il faut multiplier la matrice X par la matrice theta
leModel = model(X,theta)
plt.plot(x, leModel)

# Pour le moment notre model est dans les choux. C'est normal nous avons créer theta aléatoirement
#plt.show()


#--------------------------------------------------------------
# Fonction cout
#--------------------------------------------------------------

#On créer une fonction
def cost_function(X,y,theta):
    #m est le nombre de ligne dans notre jeu de donnée. C'est donc aussi la longueur de y
    m = len(y)
    return 1/(2*m) * np.sum((model(X,theta) - y)**2)

cout = cost_function(X,y,theta)

print(cout)

#--------------------------------------------------------------
# Gradients et descente de gradient
#--------------------------------------------------------------

#formul du gradient
def grad(X,y,theta):
    m = len(y)
    return 1/m * X.T.dot(model(X,theta) - y)

#formule de la descente de gradient
def gradient_descent(X,y,theta,learning_rate,n_iteration):
    #on ajoute cost_history juste pour voir la courbe des cout --> Correspond a l'apprentissage de la machine
    #on initialise la variable avec des zero et a chaque iteration on va sauvgarder le theta obtenu
    cost_history = np.zeros(n_iteration)
    for i in range(0, n_iteration):
        theta = theta - learning_rate * grad(X,y,theta)
        cost_history[i] = cost_function(X,y,theta)
    return theta, cost_history

#Grace a la descente de gradient on calcul le theta final
theta_final1, cost_history = gradient_descent(X,y,theta,0.001,1000)
print(theta_final1)
#On refait le model avec le theta obtenu
prediction1 = model(X,theta_final1)
plt.plot(x, prediction1,c='r')

#le resultat n'est pas assez precis alors on modifie le learnnig_rate (on pourait aussi toucher a n_iteration
theta_final2,cost_history = gradient_descent(X,y,theta,0.01,1000)
print(theta_final2)
prediction2 = model(X,theta_final2)
plt.plot(x, prediction2,c='g')

plt.show()

#pour visualiser l'apprentissage de la machine
plt.plot(range(1000), cost_history)
plt.show()

#--------------------------------------------------------------
# coefficient de determination --> Methode des moindres carré
#--------------------------------------------------------------

#Permet de monter la justesse de notre model --> Plus le resultat est proche de 1 et meilleur c'est


def coef_determination(y,pred):
    u=((y-pred)**2).sum()
    v=((y-y.mean())**2).sum()
    return 1 - u/v

print(coef_determination(y,prediction2))
