function(doc) {
    if (doc.documentType == 'Bucket') {
        emit(doc.uid, null);
    }
}