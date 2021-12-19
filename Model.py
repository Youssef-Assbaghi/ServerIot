from sklearn.datasets import make_regression
import numpy as np
import pandas as pd
from sklearn import preprocessing
from sklearn.model_selection import train_test_split
from sklearn.metrics import r2_score
from sklearn.linear_model import LogisticRegression
import pickle
import sys
# Visualitzarem només 3 decimals per mostra
pd.set_option('display.float_format', lambda x: '%.3f' % x)

# Funcio per a llegir dades en format csv
def load_dataset(path):
    dataset = pd.read_csv(path, header=0, delimiter=';')
    return dataset


def entrenar():
    dataset = load_dataset('Libro1.csv')
    x=dataset.values
    min_max_scaler=preprocessing.MinMaxScaler()
    x_scale=min_max_scaler.fit_transform(x)
    df=pd.DataFrame(x_scale,columns=dataset.columns) 
    train,test=train_test_split(df,test_size=0.1)
    entrenar,validar=train_test_split(train,test_size=0.3)
    y=entrenar.Sensacion 
    entrenar.drop('Sensacion',inplace=True,axis=1)
    regr = Logregression(entrenar, y) 
    z=validar.Sensacion
    validar.drop('Sensacion',inplace=True,axis=1)
    predicted = regr.predict(validar)
    
    MSE = mse(z, predicted)
    r2 = r2_score(z, predicted)
    save(regr)
    
def save(regr):
    filename = "C:/Users/Youssef/Desktop/Proyecto Final/DS_PRACTICA/src/Modelo/model_etrenado.sav"
    pickle.dump(regr, open(filename, 'wb'))
    
def mean_squeared_error(y1, y2):
    # comprovem que y1 i y2 tenen la mateixa mida
    assert(len(y1) == len(y2))
    mse = 0
    for i in range(len(y1)):
        mse += (y1[i] - y2[i])**2
    return mse / len(y1)

def mse(v1, v2):
    return ((v1 - v2)**2).mean()

def Logregression(x, y):
    # Creem un objecte de regressió de sklearn
    regr = LogisticRegression()

    # Entrenem el model per a predir y a partir de x
    regr.fit(x, y)

    # Retornem el model entrenat
    return regr
        
if __name__ == "__main__":
    filename = 'model_etrenado.sav'
    #print(sys.argv)
    loaded_model = pickle.load(open(filename, 'rb'))
    pruebas=[[float(sys.argv[1]),float(sys.argv[2])]]
    predicted = loaded_model.predict(pruebas)
    print(predicted)

    
