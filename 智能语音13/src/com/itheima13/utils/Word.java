package com.itheima13.utils;

import java.util.List;

public class Word {
	public List<WS> ws;
	
	public class WS{
		public int bg;
		public List<CW> cw;
		public class CW{
			public float sc;
			public String w;
		}
	}
	
}
