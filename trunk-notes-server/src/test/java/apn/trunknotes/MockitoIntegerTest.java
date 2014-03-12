package apn.trunknotes;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class MockitoIntegerTest {

    @Test
    public void shouldReturnNull() throws Exception {
        IntegerTester mock = mock(IntegerTester.class);
        assertThat(mock.getInt(),is(nullValue()));
    }

    private class IntegerTester{
        public Integer getInt(){
            return -1;
        }
    }
}
