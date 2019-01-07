# Groupe A - Projet Cloud Maxence BERNIER, Kristoffer CLASEN, Valentin CHABRIER
![build](https://travis-ci.org/BernierMaxence/Groupe_A_Cloud_Polytech.svg?branch=master)
![quality-badge](https://sonarcloud.io/api/project_badges/measure?project=BernierMaxence_Groupe_A_Cloud_Polytech&metric=alert_status)
![coverage](https://sonarcloud.io/api/project_badges/measure?project=BernierMaxence_Groupe_A_Cloud_Polytech&metric=coverage)
![vuln](https://sonarcloud.io/api/project_badges/measure?project=BernierMaxence_Groupe_A_Cloud_Polytech&metric=vulnerabilities)

* Api CRUD SpringBoot
* DB MongoDB
* CI/CD Travis-ci.org
* Analyse de code SonarCloud
* Metrics Prometheus + Grafana
* CD on CleverCloud


# Procédures pour réinstaller l'application sur CleverCloud


## étape 1 (récupération du script)
Récupérer le script reinstall.sh auprès d'une personne compétente :p
Et copiez le script à la racine du projet.

## étape 2 (installation de clever-cli)

Se rendre à la page suivante pour suivre les instructions en fonction de votre OS : https://www.clever-cloud.com/doc/clever-tools/getting_started/

Ouvrez un terminal puis tapez la commande :

```bash
clever login 
```

Entrez vos identifiants dans la page ouverte.


## étape 3 (lancement du script)
Se mettre à la racine du projet.

Execution du script : 
```bash
chmod +x reinstall.sh
```
```bash
./reinstall.sh
```

Si le script demande de remplacer le fichier configuration Clever Cloud ".clever.json" répondez oui. 
Un backup sera automatiquement créé de l'ancienne configuration.

## étape 4 (vérifications)

Se rendre dans l'interface d'administration de CleverCloud à l'adresse suivante : https://console.clever-cloud.com/

Vérifier la bonne création de l'application.

* Se rendre dans "Domains name" et vérifier que https://groupe-a-cloud-polytech.cleverapps.io/ est bien ajouté.

* Se rendre dans "Environment variables" et vérifier que les variables d'environnement suivantes sont bien présentes :
JAVA_VERSION, PORT, mongo_uri	 

## étape 5 (Mettre à jour la configuration travis)

Pour assurer le bon fonctionnement de la CD, il faut modifier l'url de déploiement du script de déploiement de l'application. 
Se rendre dans l'interface d'administration de CleverCloud dans l'onglet "Information".
Copier le champ "Deployment URL".
Se rendre dans le répertoire de l'application sur votre machine. Puis ouvrir deployCleverCloud.sh. 
Remplacer l'url de déploiement à la 5ème ligne par celle copiée précédemment.
