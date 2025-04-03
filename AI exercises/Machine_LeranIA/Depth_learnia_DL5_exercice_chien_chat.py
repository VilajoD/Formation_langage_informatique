import numpy as np
import matplotlib.pyplot as plt
from sklearn.datasets import make_blobs
from sklearn.metrics import accuracy_score
from sklearn.metrics import log_loss
from utilities import *

#récuperation des données dans les fichiers
X_train, y_train, X_test, y_test = load_data()

#vérification des données du "train"
print('demension de X_train : ',X_train.shape)
print('demension de y_train : ',y_train.shape)
print(np.unique(y_train, return_counts=True))

plt.scatter(X_train[:,0],X_train[:,1])
plt.show()

#vérification des données du "test"
print('demension de X_test : ',X_test.shape)
print('demension de y_test : ',y_test.shape)
print(np.unique(y_test, return_counts=True))

plt.scatter(X_test[:,0],X_test[:,1])
plt.show()

#Pour afficher les 10 premieres photos du dataset
plt.figure(figsize=(16,8))
for i in range(1,10):
    plt.subplot(4,5,i)
    plt.imshow(X_train[i], cmap='gray')
    plt.title(y_train[i])
    plt.tight_layout()
plt.show()


def normalisation(X):
    return X / 255


def initialisation (X):
    W = np.random.randn(X.shape[1],1)
    b = np.random.randn(1)
    return (W,b)

def model(X,W,b):
    Z = X.dot(W) + b
    A = 1/(1+np.exp(-Z))
    return A


def gradients (A,X,y):
    dW = 1/len(y) * np.dot(X.T, A-y)
    db = 1/len(y) * np.sum(A-y)
    return (dW,db)


def update(dW,db,W,b,learning_rate):
    W = W - learning_rate * dW
    b = b - learning_rate * db
    return(W, b)


def artificial_neuron(X, y, learning_rate=0.1, n_iter=100):
    W, b = initialisation(X)
    Loss = []

    for i in range(n_iter):
        A = model(X, W, b)
        Loss.append(log_loss(y, A))
        dW, db = gradients(A, X, y)
        W, b = update(dW, db, W, b, learning_rate)

    y_pred = predict(X, W, b)
    print(accuracy_score(y, y_pred))

    plt.plot(Loss)
    plt.show()

    return (W, b)


def predict(X, W, b):
    A = model(X, W, b)
    # Loss= log_loss(y,A)
    # plt.plot(Loss)
    # plt.show()
    # print(Loss)
    return A >= 0.5


#Applatir les photos. Pour que la machine puisse traiter les images il faut lui
#donner une sequence. On va donc applatir les images. Tous les pixel seront
#sur une seule ligne

def aplatir (X,y):
    X_aplati = []
    for i in range(len(y)):
        X_aplati.append(X[i].flatten())
    return X_aplati



#Normalisation des données. Les données vont de 0 à 255 or nous les voulons de 0 à 1
X_train_normal = normalisation(X_train)
X_test_normal = normalisation(X_test)
#print(X_train_normal[0])

#Aplatir les images
X_train_aplati = np.array(aplatir(X_train_normal, y_train))
X_test_aplati = np.array(aplatir(X_test_normal, y_test))

print(X_test_aplati.shape)
print(len(y_train))


Loss = []

W,b = initialisation(X_train_aplati)
A = model(X_train_aplati,W,b)
Loss.append(log_loss(y_train,A))
dW,db = gradients(A,X_train_aplati,y_train)
W,b = update(dW,db,W,b,0.1)


#Fait tourner le model
W_final, b_final = artificial_neuron(X_train_aplati, y_train, 0.03, 2000)
print(W_final, b_final)


#Test de predire un resultat d'une nouvelle donnée
y_pred = predict(X_test_aplati,W_final,b_final)
print(accuracy_score(y_test,y_pred))





