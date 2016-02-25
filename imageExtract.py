from urllib2 import *
import sys

count = 0
username = sys.argv[1]
password = sys.argv[1]

def getURLdata():
    global username,password
    url = 'http://imagecat.dyndns.org/solr/imagecat/select?q=mainType%3Aimage&wt=python&indent=true'
    #url = 'http://imagecat.dyndns.org/solr/imagecat/select?q=mainType%3A%22image%22&rows=100&wt=python&indent=true'

    p = HTTPPasswordMgrWithDefaultRealm()
    p.add_password(None, url, username, password)
    handler = HTTPBasicAuthHandler(p)
    opener = build_opener(handler)
    install_opener(opener)

    conn = urlopen(url)
    rsp = eval(conn.read())

    return rsp


def sendResponse(rsp):
    print rsp['response']['docs'][0]['id']

    for record in rsp['response']['docs']:
        path = record['id']
        getImages(path)


def getImages(path):
    global count, username, password
    count += 1
    #print len(path), len(path.strip())
    #raw_input()
    path = path.strip()
    imagePath = path.replace('file:/data2/USCWeaponsStatsGathering/nutch/full_dump/','http://imagecat.dyndns.org/weapons/alldata/')

    p = HTTPPasswordMgrWithDefaultRealm()
    p.add_password(None, imagePath, username, password)
    handler = HTTPBasicAuthHandler(p)
    #handler = HTTPDigestAuthHandler(p)
    opener = build_opener(handler)
    install_opener(opener)

    print imagePath
    #with open(imagePath.split('/')[-1] + '.jpeg','wb') as image:
    with open(str(count) + '.jpeg','wb') as image:
        image.write(urlopen(imagePath).read())


def getFromFile(filename):
    with open(filename,'r+') as urlFile:
        for url in urlFile:
            getImages(url)


def main():
    #response = getURLdata()
    #sendResponse(response)
    getFromFile('SerialImagesExtract/urlList.txt')

if __name__ == '__main__':
    main()
