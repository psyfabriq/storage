package pfq.storage.server.service.impl;

import org.springframework.stereotype.Service;

@Service
public class QBuilder {
	private StringBuilder sbquery;
	private Builder b;
	
	public QBuilder() {
		sbquery = new StringBuilder();
	}

	public Builder getBuilder() {
		if(b==null) {
			b = this.new Builder();
		}else { sbquery.delete(0, sbquery.length()); }
		return b;
	}
	
	public static String LowerCase(String t) {
    	StringBuilder sb = new  StringBuilder();
    	sb.append("{$regex:'");
    	sb.append(t);
    	sb.append("',$options:'i'}");
    	return sb.toString();
    }
	
	public static String Besides(String t) {
    	StringBuilder sb = new  StringBuilder();
    	sb.append("{'$ne':'");
    	sb.append(t);
    	sb.append("'}");
    	return sb.toString();
    }
	
	public class Builder {
		private Builder() { }
		public Builder append(String t) {
			QBuilder.this.sbquery.append(t);
			return this;
		}
		
		public String build() {
			return QBuilder.this.sbquery.toString();
		}
	}
}
