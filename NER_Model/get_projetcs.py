import requests
import json
import time


#parametres
api_key = "tH_JPiY0Y3fL"
Project_Token = "tTyTiU56Or3v"

params = {
  "api_key": api_key,
  "offset": "0",
  "limit": "20",
  "include_options": "1"
}
r = requests.get('https://parsehub.com/api/v2/projects', params=params)

response = r.json()



for i in response['projects']:
    print(i['token'],"  ,    ",i['title'])