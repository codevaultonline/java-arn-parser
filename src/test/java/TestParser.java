import online.codevault.com.amazon.aws.arnparser.ARN;
import online.codevault.com.amazon.aws.arnparser.ARNParserException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestParser {

    private String urn = "arn:aws:s3:::bucket/key";

    @Test
    public void parse() throws ARNParserException {

        ARN arn = ARN.parse(this.urn);

        assertEquals("aws", arn.getPartition());
        assertEquals("s3", arn.getService());
        assertNull(arn.getRegion());
        assertNull(arn.getAccountId());
        assertEquals("bucket", arn.getResourceType());
        assertEquals("key", arn.getResourceId());
        assertTrue(arn.isSlashResource());

    }

    @Test
    public void outputAsString() throws ARNParserException {

        ARN arn = ARN.parse(urn);
        assertEquals(urn, arn.toString());

    }

}
