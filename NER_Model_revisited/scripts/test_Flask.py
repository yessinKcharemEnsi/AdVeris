from flask import Flask
import spacy 
from flask import request, jsonify

app = Flask(__name__)


@app.route("/", methods=['GET'])
def predict():
    query_parameters = request.args
    data = request.json
    desc  = data['desc']
    print(desc , type(desc))

    return 5
    



if __name__ == '__main__':
    app.run(debug=True)
    nlp = spacy.load(R"output/model-best")
    if nlp != None : 
        print("model loaded succesfully ")
    else :
        print("no existing model to load")
