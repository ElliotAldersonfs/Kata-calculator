import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main
{
    private enum RomanNumeral {
        I(1), IV(4), V(5), IX(9), X(10),
        XL(40), L(50), XC(90), C(100),
        CD(400), D(500), CM(900), M(1000);

        private int value;

        RomanNumeral(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static List getReverseSortedValues() {
            return Arrays.stream(values())
                           .sorted(Comparator.comparing((RomanNumeral e) -> e.value).reversed())
                           .collect(Collectors.toList());
        }
    }

    private static final String[] romanNumbers = new String[] {
            "I", "II", "III","IV","V","VI","VII","VIII","IX","X"
    };

    private static final String[] arabicNumbers = new String[] {
            "1", "2", "3","4","5","6","7","8","9","10"
    };

    public static void main(String[] args)
    {
        try
        {
            while (true)
            {
                System.out.println("Введите выражение:");
                Scanner scanner = new Scanner(System.in);
                String input = (scanner.nextLine()).replace(" ", "");
                String result = calc(input);
                System.out.println("Ваш результат:" + result);
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    public static String calc(String input) throws Exception
    {
        boolean isRomanCalc = false;
        String[] values = input.split("\\+|-|\\*|/");
        checkInputError(values);
        String oneNum = values[0];
        String twoNum = values[1];
        if (isRoman(oneNum) && isRoman(twoNum))
        {
            oneNum = converterToArabic(oneNum);
            twoNum = converterToArabic(twoNum);
            isRomanCalc = true;
        }
        else if ((isArabic(oneNum) && isRoman(twoNum)) || (isRoman(oneNum) && isArabic(twoNum)))
        {
            throw new Exception("используются одновременно разные системы счисления");
        }

        int one = Integer.parseInt(oneNum);
        int two = Integer.parseInt(twoNum);
        int result = 0;
        if (input.contains("+"))
        {
            result = one + two;
        }
        else if (input.contains("-"))
        {
           result = one - two;
        }
        else if (input.contains("/"))
        {
           result = one / two;
        }
        else if (input.contains("*"))
        {
            result = one * two;
        }
        if (isRomanCalc)
        {
            if (result < 0)
            {
                throw new Exception("в римской системе нет отрицательных чисел");
            }
            return arabicToRoman(result);
        }
        return String.valueOf(result);
    }

    private static void checkInputError(String[] values) throws Exception
    {
        if (values.length < 2)
        {
            throw new Exception("строка не является математической операцией");
        }
        if (values.length > 2)
        {
            throw new Exception("формат математической операции не удовлетворяет заданию - два операнда и один " +
                                        "оператор (+, -, /, *)");
        }
    }

    public static String converterToArabic(String number)
    {
        for (int i = 0; i < romanNumbers.length; i++)
        {
            if (romanNumbers[i].equals(number))
            {
                return arabicNumbers[i];
            }
        }
        return null;
    }

    public static boolean isArabic(String value){
        for (String s: arabicNumbers)
        {
            if (s.equals(value)) return true;
        }
        return false;
    }

    public static boolean isRoman(String value){
        for (String s: romanNumbers)
        {
            if (s.equals(value)) return true;
        }
        return false;
    }

    public static String arabicToRoman(int number) {
        if ((number <= 0) || (number > 4000)) {
            throw new IllegalArgumentException(number + " is not in range (0,4000]");
        }

        List<RomanNumeral> romanNumerals = RomanNumeral.getReverseSortedValues();

        int i = 0;
        StringBuilder sb = new StringBuilder();

        while ((number > 0) && (i < romanNumerals.size())) {
            RomanNumeral currentSymbol = romanNumerals.get(i);
            if (currentSymbol.getValue() <= number) {
                sb.append(currentSymbol.name());
                number -= currentSymbol.getValue();
            } else {
                i++;
            }
        }

        return sb.toString();
    }
}
