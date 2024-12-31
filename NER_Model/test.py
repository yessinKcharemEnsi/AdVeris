from flask import Flask, request, jsonify
import spacy



# Load the trained spaCy model
nlp = spacy.load(R"output/model-best")



desc = """"IDEAL POUR INVESTISSEURS, RENDEMENT NET DE 4,6% : Situé à côté du Docks Brussel, dans l’ancien familistère de Godin découvrez notre magnifique appartement offrant quatre belles chambres et trois salles de bain au quatrième et dernier étage avec ascenseur d’une très agréable copropriété de 57 appartements, le tout fraîchement rénové.
Le bien se compose comme suit : Porte sécurisé, hall d’entrée, espace de vie (salle à manger, séjour avec cuisine américaine full-équipée), hall de nuit desservant les 2 premières chambres, WC séparé, 2 salles de douches et une très grande buanderie. De l’autre côté : deuxième hall de nuit desservant la troisième et quatrième chambre avec la troisième salle de bain et WC séparé. PEB D, toutes les installations conforme (électricité, gaz,..).
Le projet UTOPIA à Bruxelles, propose une vision moderne et durable de la vie urbaine. Avec ses appartements élégants, il offre aux résidents un cadre de vie exceptionnel au cœur de la ville, tout en préservant l'environnement et en favorisant la vie communautaire.
A visiter !!"""

# Use the model to process the text
doc = nlp(desc)
entities = [{'text': ent.text, 'label': ent.label_} for ent in doc.ents]

print({'entities': entities})

