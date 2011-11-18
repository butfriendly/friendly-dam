package de.soulworks.dam.domain;

import javax.xml.bind.annotation.*;
import java.net.URI;

/**
 * @author Christian Schmitz <csc@soulworks.de>
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement
public final class Upload extends EktorpBaseDomain {
	@XmlAttribute(name = "uid")
	private String uid;

	@XmlAttribute(name = "bucket_uid")
	private String bucketUid;

	@XmlElement
	private URI url;

	@XmlAttribute(name = "status")
	private UploadStatus status;

	public Upload() {
		super();
	}

	public Upload(String uid) {
		this.uid = uid;
	}

	public static Upload create() {
		return new Upload();
	}

	public static Upload create(String uid) {
		return Upload.create().setUid(uid);
	}

	public String getUid() {
		return uid;
	}

	public Upload setUid(String uid) {
		this.uid = uid;
		return this;
	}

	public UploadStatus getStatus() {
		return status;
	}

	public Upload setStatus(UploadStatus status) {
		this.status = status;
		return this;
	}

	public URI getUrl() {
		return url;
	}

	public Upload setUrl(URI url) {
		this.url = url;
		return this;
	}

	public String getBucketUid() {
		return bucketUid;
	}

	public Upload setBucketUid(String bucketUid) {
		this.bucketUid = bucketUid;
		return this;
	}
}
