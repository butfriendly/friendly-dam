The DAM API
===========


Methods
=======

/buckets
--------

A bucket is a container for assets. Every bucket is assigned to a customer.

### GET <>

Returns all available buckets at the DAM.

### POST <>

Creates a new bucket at the DAM.

/buckets/<>
-----------

### POST <>

Import an asset

#### Examples

##### Simple import

Imports a single asset to the buckets. The properties of the originating asset
gets copied to a new one.

	<asset uid="ABCDEF" />

The response doesn't contain any response body. Just a Location-header.

/assets
-------

### GET <>

Returns all abailable assets at the DAM.

### POST <>

Creates a new asset at the DAM.

/assets/<>
----------

### GET <>

Returns details on the given asset.

### PUT <>

Updates the details of the given asset.

/customers
----------

### GET <>

Returns all available customers at the DAM.

### POST <>

Creates a new customer at the DAM.

/customers/<>/accounts
----------------------

### GET <>

Returns all accounts of the given customer.

### POST <>

Creates a new account at the given customer.

/customers/<>/accounts/<>
-------------------------

### GET

Returns a single account.

### PUT

Updates the details of the given account.