package com.ax.bedcon;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import io.dropwizard.testing.junit.ResourceTestRule;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.mockito.Matchers;

import com.ax.bedcon.entity.Hipster;
import com.ax.bedcon.entity.Hipster.JeansType;
import com.ax.bedcon.resource.HipsterResource;
import com.google.common.base.Optional;

public class HipsterResourceTest {

	private static final HipsterStore store = mock(HipsterStore.class);

	@ClassRule
	public static final ResourceTestRule resources = ResourceTestRule.builder()
			.addResource(new HipsterResource(store)).build();

	private Hipster hipster;

	@Before
	public void setup() {
		hipster = new Hipster(4, "Foo", JeansType.SKINNY, true, "image.jpg");

		when(store.get(Matchers.eq("Foo"))).//
				thenReturn(Optional.of(hipster));
	}

	@Test
	public void testGetHipster() {
		assertEquals(resources.client().resource("/hipsters/Foo")//
				.get(Hipster.class), hipster);

		verify(store).get("Foo");
	}

}
