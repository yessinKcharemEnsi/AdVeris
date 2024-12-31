"""runs the Parsehub scraper (identified by Project_Token) and fetch the data into raw_data/sample.txt (append it)"""
import requests
import time
from clean_text import clean_text
 	
#parametres
api_key = "tH_JPiY0Y3fL"
Project_Token = "t1r9GvFQQTer"

#run a project
params = {
  "api_key": api_key,
  "start_template": "main_template",
  "send_email": "1"
}
r = requests.post("https://www.parsehub.com/api/v2/projects/{}/run".format(Project_Token), data=params)


response = r.json()

#get the run token
Run_Token = response['run_token']

print("Parsehub scraper with token :",Run_Token," is running...")

#check if the run is completed succefully
data_ready = False
while(data_ready == False) :

    params = {
    "api_key": api_key
    }
    r = requests.get('https://www.parsehub.com/api/v2/runs/{}'.format(Run_Token), params=params)
    response = r.json()

    data_ready = response['data_ready']
    print("data ready :" ,data_ready) #0 if not ready , 1 if ready 
    time.sleep(2)


#get data for the run
import requests

params = {
  "api_key": api_key,
  "format": "json"
}
r = requests.get('https://www.parsehub.com/api/v2/runs/{}/data'.format(Run_Token), params=params)

data = r.json()

# Open a file with access mode 'a'
training_file = open('raw_data/train.txt', 'a',encoding='UTF-8')
validation_file = open('raw_data/validate.txt', 'a',encoding='UTF-8')

#data scraped will be devided into training and testing 
total = len(data['annonce'])
training = int ((total*80)/100)
validation = int((total*20)/100)



for i in range (training):
  if data['annonce'][i] != {} :
    ligne = data['annonce'][i]['description'].replace("\n"," ")
    ligne  = clean_text(ligne)
    print(ligne)
    training_file.write(ligne)
    training_file.write('\n')




for i in range (validation):
  i = i + training
  if data['annonce'][i] != {} :
    ligne = data['annonce'][i]['description'].replace("\n"," ")
    ligne  = clean_text(ligne)
    validation_file.write(ligne)
    validation_file.write('\n')




# Close the files
training_file.close()
validation_file.close()









