function(doc) {
    if (doc.documentType == 'Asset') {
        emit(doc.uid, null);
    }
}