package online.codevault.com.amazon.aws.arnparser;

import org.apache.commons.lang3.StringUtils;

public class ARN {

    private String partition;
    private String service;
    private String region;
    private String accountId;
    private String resourceType;
    private String resourceId;
    private boolean isSlashResource = false;

    public ARN(String partition, String service, String region, String accountId, String resource) {

        this.setPartition(partition);
        this.setService(service);
        this.setRegion(region);
        this.setAccountId(accountId);
        this.setResource(resource);

    }

    private void setResource(String resource) {

        if (resource.contains("/")) {

            String[] resourceParts = resource.split("/", 2);
            this.resourceType = resourceParts[0];
            this.resourceId = resourceParts[1];
            this.isSlashResource = true;

        } else if (resource.contains(":")) {

            String[] resourceParts = resource.split(":", 2);
            this.resourceType = resourceParts[0];
            this.resourceId = resourceParts[1];
            this.isSlashResource = false;

        } else {

            this.resourceId = resource;
            this.isSlashResource = false;

        }

    }

    public static ARN parse(String arn) throws ARNParserException {

        String[] arnParts = arn.split(":");

        if (arnParts.length < 6) {
            throw new ARNParserException("There are not enough colon separated parts in ARN to be valid.");
        }

        String resource;

        if (7 == arnParts.length) {
            resource = arnParts[5] + ":" + arnParts[6];
        } else {
            resource = arnParts[5];
        }

        return new ARN(StringUtils.trimToNull(arnParts[1]), StringUtils.trimToNull(arnParts[2]), StringUtils.trimToNull(arnParts[3]), StringUtils.trimToNull(arnParts[4]), StringUtils.trimToNull(resource));

    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder("arn");

        sb.append(":").append(partition);
        sb.append(":").append(service);
        sb.append(":").append(StringUtils.defaultString(region,""));
        sb.append(":").append(StringUtils.defaultString(accountId,""));

        if (isSlashResource) {
            sb.append(":").append(resourceType).append("/").append(resourceId);
        } else if (null == resourceType) {
            sb.append(":").append(resourceId);
        } else {
            sb.append(":").append(resourceType).append(":").append(resourceId);
        }

        return sb.toString();

    }

    public String getPartition() {
        return partition;
    }

    public void setPartition(String partition) {
        this.partition = partition;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public boolean isSlashResource() {
        return isSlashResource;
    }

    public void setSlashResource(boolean slashResource) {
        isSlashResource = slashResource;
    }

}
