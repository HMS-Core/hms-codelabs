## Table of Contents

 * [Introduction](#introduction)
 * [Installation](#installation)
 * [Configuration ](#configuration )
 * [Supported Environments](#supported-environments)
 * [Sample Code](# Sample Code)
 * [License](#license)

### Introduction
    This is sample code for how to use the HMS wallet server interface. The HMS wallet server interface contains REST APIs for six types of passes (Loyalty Card, Offer, Gift Card, Boarding Pass, Transit Pass, Event Ticket).
    You can use these REST APIs to implement operations such as adding, querying or updating. Before you use this Demo, it's assumed that you already have a HUAWEI developer account and have already created an app to implement the
    HMS wallet kit. If you haven't, please refer to https://developer.huawei.com/consumer/en/doc/start/10104 and https://developer.huawei.com/consumer/en/doc/development/AppGallery-Connect/agc-create_app. Refer to
    https://developer.huawei.com/consumer/en/doc/development/HMS-Guides/wallet-preparations to apply for a service for one of the six passes. 

### Installation
    Before running the Demo code, you should have installed Java and Maven.

## Supported Environments
    Oracle Java 1.8.0.211 or above is recommended.

## Configuration 
    1. To run the sample code, you need to set the following configuration values in the "src\test\resources\release.config.properties" file.

        1). "gw.appid" and "gw.appid.secret":
        To implement the HMS wallet kit to an app, "gw.appid" and "gw.appid.secret" are this app's "App ID" and "App secret" on the HUAWEI AppGallery Connect website.

        2). "walletServerBaseUrl":
        Set this value depending on your account location. Get it from https://developer.huawei.com/consumer/en/doc/development/HMS-References/wallet-api-server-address.

    2. Edit JSON files in the "config/hmspass" directory. Set the Service ID you applied on AGC websit as "passTypeIdentifier".
        
    3. After you set all the configurations values, compile the Demo as a Maven project.

    4. Then copy the "config/hmspass" directory to the "target/classes" directory. Now you can start to test example methods in the Demo code.

## Sample Code
    Sample methods of using the HMS wallet server interface are listed below. You can follow these examples to operate the passes.
    1. Loyalty card:
        1). Register a Loyalty Card Model.
        You can register a loyalty card model by calling the "createLoyaltyModel" method.

        2). Query a Loyalty Card Model.
        You can query a loyalty card model by calling the "getLoyaltyModel" method.

        3). Query Loyalty Card Models.
        You can query loyalty card models registered in your app by calling the "getLoyaltyModelList" method.

        4). Overwrite a Loyalty Card Model.
        You can overwrite a loyalty card model by calling the "fullUpdateLoyaltyModel" method.

        5). Update part of a Loyalty Card Model.
        You can update part of a loyalty card model by calling the "partialUpdateLoyaltyModel" method.

        6). Add Messages to a Loyalty Card Model.
        You can add messages to a loyalty card model by calling the "addMessageToLoyaltyModel" method.

        7). Add a Loyalty Card Instance.
        You can add a loyalty card instance by calling the "createLoyaltyInstance" method.

        8). Query a Loyalty Card Instance.
        You can query a loyalty card instance by calling the "getLoyaltyInstancemethod" method.

        9). Query Loyalty Card Instances.
        You can query loyalty card instances belongs to one of your loyalty card models by calling the "getLoyaltyInstanceList" method.

        10). Overwrite a Loyalty Card Instance.
        You can overwrite a loyalty card instance by calling the "fullUpdateLoyaltyInstance" method.

        11). Update part of a Loyalty Card Instance.
        You can update part of a loyalty card instance by calling the "partialUpdateLoyaltyInstance" method.

        12). Add Messages to a Loyalty Card Instance.
        You can add messages to a loyalty card instance by calling the "addMessageToLoyaltyInstance" method.

        13). Link/Unlink an Offer Instance to/from a Loyalty Card Instance.
        You can link or unlink an offer instance to/from a loyalty card instance by calling the "updateLinkedOffersToLoyaltyInstance" method.

    2. Offer:
        1). Register an Offer Model.
        You can register an offer model by calling the "createOfferModel" method.

        2). Query an Offer Model.
        You can query an offer model by calling the "getOfferModel" method.

        3). Query Offer Models.
        You can query offer models registered in your app by calling the "getOfferModelList" method.

        4). Overwrite an Offer Model.
        You can overwrite an offer model by calling the "fullUpdateOfferModel" method.

        5). Update part of an Offer Model.
        You can update part of an offer model by calling the "partialUpdateOfferModel" method.

        6). Add Messages to an Offer Model.
        You can add messages to an offer model by calling the "addMessageToOfferModel" method.

        7). Add an Offer Instance.
        You can add an offer instance by calling the "createOfferInstance" method.

        8). Query an Offer Instance.
        You can query an offer instance by calling the "getOfferInstance" method.

        9). Query Offer Instances.
        You can query offer instances belongs to one of your offer models by calling the "getOfferInstanceList" method.

        10). Overwrite an Offer Instance.
        You can overwrite an offer instance by calling the "fullUpdateOfferInstance" method.

        11). Update part of an Offer Instance.
        You can update part of an offer instance by calling the "partialUpdateOfferInstance" method.

        12). Add Messages to an Offer Instance.
        You can add messages to an offer instance by calling the "addMessageToOfferInstance" method.

    3. Gift Card:
        1). Register a Gift Card Model.
        You can register a gift card model by calling the "createGiftCardModel" method.

        2). Query a Gift Card Model.
        You can query a gift card model by calling the "getGiftCardModel" method.

        3). Query Gift Card Models.
        You can query gift card models registered in your app by calling the "getGiftCardModelList" method.

        4). Overwrite a Gift Card Model.
        You can overwrite a gift card model by calling the "fullUpdateGiftCardModel" method.

        5). Update part of a Gift Card Model.
        You can update part of a gift card model by calling the "partialUpdateGiftCardModel" method.

        6). Add Messages to a Gift Card Model.
        You can add messages to a gift card model by calling the "addMessageToGiftCardModel" method.

        7). Add a Gift Card Instance.
        You can add a gift card instance by calling the "createGiftCardInstance" method.

        8). Query a Gift Card Instance.
        You can query a gift card instance by calling the "getGiftCardInstance" method.

        9). Query Gift Card Instances.
        You can query gift card instances belongs to one of your gift card models by calling the "getGiftCardInstanceList" method.

        10). Overwrite a Gift Card Instance.
        You can overwrite a gift card instance by calling the "fullUpdateGiftCardInstance" method.

        11). Update part of a Gift Card Instance.
        You can update part of a gift card instance by calling the "partialUpdateGiftCardInstance" method.

        12). Add Messages to a Gift Card Instance.
        You can add messages to a gift card instance by calling the "addMessageToGiftCardInstance" method.

    4. Boarding Pass:
        1). Register a Boarding Pass Model.
        You can register a boarding pass model by calling the "createFlightModel" method.

        2). Query a Boarding Pass Model.
        You can query a boarding pass model by calling the "getFlightModel" method.

        3). Query Boarding Pass Models.
        You can query boarding pass models registered in your app by calling the "getFlightModelList" method.

        4). Overwrite a Boarding Pass Model.
        You can overwrite a boarding pass model by calling the "fullUpdateFlightModel" method.

        5). Update part of a Boarding Pass Model.
        You can update part of a boarding pass model by calling the "partialUpdateFlightModel" method.

        6). Add Messages to a Boarding Pass Model.
        You can add messages to a boarding pass model by calling the "addMessageToFlightModel" method.

        7). Add a Boarding Pass Instance.
        You can add a boarding pass instance by calling the "createFlightInstance" method.

        8). Query a Boarding Pass Instance.
        You can query a boarding pass instance by calling the "getFlightInstance" method.

        9). Query Boarding Pass Instances.
        You can query boarding pass instances belongs to one of your boarding pass models by calling the "getFlightInstanceList" method.

        10). Overwrite a Boarding Pass Instance.
        You can overwrite a boarding pass instance by calling the "fullUpdateFlightInstance" method.

        11). Update part of a Boarding Pass Instance.
        You can update part of a boarding pass instance by calling the "partialUpdateFlightInstance" method.

        12). Add Messages to a Boarding Pass Instance.
        You can add messages to a boarding pass instance by calling the "addMessageToFlightInstance" method.

    5. Transit Pass:
        1). Register a Transit Pass Model.
        You can register a transit pass model by calling the "createTransitModel" method.

        2). Query a Transit Pass Model.
        You can query a transit pass model by calling the "getTransitModel" method.

        3). Query Transit Pass Models.
        You can query transit pass models registered in your app by calling the "getTransitModelList" method.

        4). Overwrite a Transit Pass Model.
        You can overwrite a transit pass model by calling the "fullUpdateTransitModel" method.

        5). Update part of a Transit Pass Model.
        You can update part of a transit pass model by calling the "partialUpdateTransitModel" method.

        6). Add Messages to a Transit Pass Model.
        You can add messages to a transit pass model by calling the "addMessageToTransitModel" method.

        7). Add a Transit Pass Instance.
        You can add a transit pass instance by calling the "createTransitInstance" method.

        8). Query a Transit Pass Instance.
        You can query a transit pass instance by calling the "getTransitInstance" method.

        9). Query Transit Pass Instances.
        You can query transit pass instances belongs to one of your transit pass models by calling the "getTransitInstanceList" method.

        10). Overwrite a Transit Pass Instance.
        You can overwrite a transit pass instance by calling the "fullUpdateTransitInstance" method.

        11). Update part of a Transit Pass Instance.
        You can update part of a transit pass instance by calling the "partialUpdateTransitInstance" method.

        12). Add Messages to a Transit Pass Instance.
        You can add messages to a transit pass instance by calling the "addMessageToTransitInstance" method.

    6. Event Ticket:
        1). Register an Event Ticket Model.
        You can register an event ticket model by calling the "createEventTicketModel" method.

        2). Query an Event Ticket Model.
        You can query an event ticket model by calling the "getEventTicketModel" method.

        3). Query Event Ticket Models.
        You can query event ticket models registered in your app by calling the "getEventTicketModelList" method.

        4). Overwrite an Event Ticket Model.
        You can overwrite an event ticket model by calling the "fullUpdateEventTicketModel" method.

        5). Update part of an Event Ticket Model.
        You can update part of an event ticket model by calling the "partialUpdateEventTicketModel" method.

        6). Add Messages to an Event Ticket Model.
        You can add messages to an event ticket model by calling the "addMessageToEventTicketModel" method.

        7). Add an Event Ticket Instance.
        You can add an event ticket instance by calling the "createEventTicketInstance" method.

        8). Query an Event Ticket Instance.
        You can query an event ticket instance by calling the "getEventTicketInstance" method.

        9). Query Event Ticket Instances.
        You can query event ticket instances belongs to one of your event ticket models by calling the "getEventTicketInstanceList" method.

        10). Overwrite an Event Ticket Instance.
        You can overwrite an event ticket instance by calling the "fullUpdateEventTicketInstance" method.

        11). Update part of an Event Ticket Instance.
        You can update part of an event ticket instance by calling the "partialUpdateEventTicketInstance" method.

        12). Add Messages to an Event Ticket Instance.
        You can add messages to an event ticket instance by calling the "addMessageToEventTicketInstance" method.

    7. Generate JSON Web Encryption (JWE):
        You can call the "generateThinJWEToBindUser" or "generateJWEToAddPassAndBindUser" methods to generate JWEs. These JWEs are used to bind wallet pass instances to users.

### License
HMS wallet server sample code is licensed under the [Apache License, version 2.0](http://www.apache.org/licenses/LICENSE-2.0).
