function(doc) {
    if (doc.documentType == 'Asset') {
        emit(doc.bucketId, null);
    }
}