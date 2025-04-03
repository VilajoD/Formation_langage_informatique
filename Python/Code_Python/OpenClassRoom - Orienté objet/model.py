import json
import math

class Agent:

    def say_hello(self,country_name):
        return "Bonjour, bienvenue en " + country_name + " ! "

    def __init__(self, position, **agent_attributes):
        self.position = position
        for nom_attrib, valeur_attrib in agent_attributes.items():
            setattr(self, nom_attrib, valeur_attrib)

class Position:

    def __init__(self, latitude_degrees, longitude_degrees):
        self.latitude_degrees = latitude_degrees
        self.longitude_degrees = longitude_degrees

    @property
    def latitude(self):
        return self.latitude_degrees * math.pi / 180

    @property
    def longitude(self):
        return self.latitude_degrees * math.pi / 180

class Zone:

    ZONE = []
    MAX_LONGITUDE = 180
    MIN_LONGITUDE = -180
    MAX_LATITUDE = 90
    MIN_LATITUDE = -90
    LARGEUR = 1
    HAUTEUR = 1


    def __init__(self, coin1, coin2):
        self.coin1 = coin1
        self.coin2 = coin2
        self.habitant = []


    def add_inhabitan(self,agent):
        self.habitant.append(agent)

    @property
    def population(self):
        return len(self.habitant)

    @classmethod
    def init_zone(cls):
        for latitude in range(cls.MIN_LATITUDE, cls.MAX_LATITUDE, cls.HAUTEUR):
            for longitude in range(cls.MIN_LONGITUDE, cls.MAX_LONGITUDE, cls.LARGEUR):
                coin_bas_g = Position(longitude,latitude)
                coin_haut_d = Position(longitude + cls.LARGEUR, latitude + cls.HAUTEUR )
                zone = Zone(coin_bas_g,coin_haut_d)
                cls.ZONE.append(zone)

    def contains(self, position):
        return position.longitude >= min(self.coin1.longitude, self.coin2.longitude) and \
               position.longitude < max(self.coin1.longitude, self.coin2.longitude) and \
               position.latitude >= min(self.coin1.latitude, self.coin2.latitude) and \
               position.latitude < max(self.coin1.latitude, self.coin2.latitude)

    @classmethod
    def find_zone_that_contains(cls, position):
        # Compute the index in the ZONES array that contains the given position
        longitude_index = int((position.longitude_degrees - cls.MIN_LONGITUDE) / cls.LARGEUR)
        latitude_index = int((position.latitude_degrees - cls.MIN_LATITUDE) / cls.HAUTEUR)
        longitude_bins = int(
            (cls.MAX_LONGITUDE - cls.MIN_LONGITUDE) / cls.HAUTEUR)  # 180-(-180) / 1
        zone_index = latitude_index * longitude_bins + longitude_index

        # Just checking that the index is correct
        zone = cls.ZONE[zone_index]
        assert zone.contains(position)

        return zone

def main():
    Zone.init_zone()
    # Création d'une instance pour chaque agent dans le fichier.
    for agent_attributes in json.load(open("agents-100k.json")):
        latitude = agent_attributes.pop("latitude")
        longitude = agent_attributes.pop("longitude")
        position = Position(latitude,longitude)
        agent = Agent(position, **agent_attributes)
        #print(agent.say_hello(agent.country_name) + "Votre niveau d'agréabilité est de " + str(agent.agreeableness))
        #print(agent.position.latitude)
        zone = Zone.find_zone_that_contains(position)
        zone.add_inhabitant(agent)
        print(zone.population)


main()
