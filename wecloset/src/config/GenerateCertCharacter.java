package config;

import java.util.Random;

public class GenerateCertCharacter{
	
    private int certCharLength = 18;
    private final char[] characterTable = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 
                                            'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 
                                            'Y', 'Z', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0' };
    
	private static GenerateCertCharacter instance;
	
	private GenerateCertCharacter(){}

	public static GenerateCertCharacter getInstance() {		
		if (instance == null)
			instance = new GenerateCertCharacter();			
		return instance;
	}
	    
    
    public String excuteGenerate() {
        Random random = new Random(System.currentTimeMillis());
        int tablelength = characterTable.length;
        StringBuffer buf = new StringBuffer();
        
        for(int i = 0; i < certCharLength; i++) {
            buf.append(characterTable[random.nextInt(tablelength)]);
        }
                
        return buf.toString();
    }
    
    
    public String excuteGenerate6() {
        Random random = new Random(System.currentTimeMillis());
        int tablelength = characterTable.length;
        StringBuffer buf = new StringBuffer();
        
        for(int i = 0; i < certCharLength; i++) {
            buf.append(characterTable[random.nextInt(tablelength)]);
        }
        
        String str = buf.toString(); 
        String[] splitStr = str.split("(?<=\\G.{" + 6 + "})"); // 6글자마다 잘라내어 배열 생성
        str = String.join("-", splitStr);// 배열 원소 사이에  - 구분자를 추가하며 하나의 문자열로 이어붙임
                
        return str;
    }

    public int getCertCharLength() {
        return certCharLength;
    }

    public void setCertCharLength(int certCharLength) {
        this.certCharLength = certCharLength;
    }
}


