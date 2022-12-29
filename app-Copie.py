import numpy as np
from flask import Flask, request, jsonify, render_template
import pickle
import pandas as pd
import requests
import json
import nltk
import string 
nltk.download('punkt')
nltk.download('stopwords')
from sklearn.feature_extraction.text import TfidfVectorizer
stemmer = nltk.stem.porter.PorterStemmer()
remove_punctuation_map = dict((ord(char),None) for char in string.punctuation)
from nltk.corpus import stopwords
stopwords = stopwords.words('french')
print(stopwords)

app = Flask(__name__)

def stem_tokens(text):
     return [stemmer.stem(token) for token in text]

def normilize(text):
     return stem_tokens(nltk.word_tokenize(text.lower().translate(remove_punctuation_map)))

def cosine_sim(text1,text2):
    vectorizer=TfidfVectorizer(tokenizer=normilize,
                              stop_words=stopwords,
                              ngram_range=(1,1))
    tfidf=vectorizer.fit_transform([text1,text2])
    return((tfidf*tfidf.T).A)[0,1]

assV="essence, gazoil, ABS, 5 chauvaux, 5 portes, vitres, batterie"
assB="fonds, business, local"
assE="master education diplôme licence"
assVA="vacances voyage vie maladie"
L=[assV,assB,assE,assVA]
@app.route('/simv/<id>',methods=['GET'])
def simv(id):

    r = requests.get('http://localhost:8082/banque-en-ligne/Assurance/getDesc/'+id,headers={'content-type':'application/json'})
    print(r)
    desc=str(r.content)
    
    r1 = cosine_sim(desc,assV)
    print(r1)
    r2 = cosine_sim(desc,assB)
    print(r2)
    r3 = cosine_sim(desc,assE)
    print(r3)
    r4 = cosine_sim(desc,assVA)
    print(r4)
    

    return jsonify(r1)


@app.route('/simb/<id>',methods=['GET'])
def simb(id):

    r = requests.get('http://localhost:8082/banque-en-ligne/Assurance/getDesc/'+id,headers={'content-type':'application/json'})
    print(r)
    desc=str(r.content)
    
    r1 = cosine_sim(desc,assV)
    print(r1)
    r2 = cosine_sim(desc,assB)
    print(r2)
    r3 = cosine_sim(desc,assE)
    print(r3)
    r4 = cosine_sim(desc,assVA)
    print(r4)
    

    return jsonify(r2)
    
@app.route('/sime/<id>',methods=['GET'])
def sime(id):

    r = requests.get('http://localhost:8082/banque-en-ligne/Assurance/getDesc/'+id,headers={'content-type':'application/json'})
    print(r)
    desc=str(r.content)
    
    r1 = cosine_sim(desc,assV)
    print(r1)
    r2 = cosine_sim(desc,assB)
    print(r2)
    r3 = cosine_sim(desc,assE)
    print(r3)
    r4 = cosine_sim(desc,assVA)
    print(r4)
    

    return jsonify(r3)


@app.route('/simva/<id>',methods=['GET'])
def simva(id):

    r = requests.get('http://localhost:8082/banque-en-ligne/Assurance/getDesc/'+id,headers={'content-type':'application/json'})
    print(r)
    desc=str(r.content)
    
    r1 = cosine_sim(desc,assV)
    print(r1)
    r2 = cosine_sim(desc,assB)
    print(r2)
    r3 = cosine_sim(desc,assE)
    print(r3)
    r4 = cosine_sim(desc,assVA)
    print(r4)
    

    return jsonify(r4)

if __name__ == "__main__":
    app.run(debug=True)