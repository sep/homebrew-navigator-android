classes = {
  "RECIPES" => [{name: "theRecipes", required: true, type: 'List<RECIPE>', elementtag: "@ElementList"}],
  "MISCS" => [{name: "theMiscs", required: true, type: 'List<MISC>', elementtag: "@ElementList"}],
  "STYLES" => [{name: "theStyles", required: true, type: 'List<STYLE>', elementtag: "@ElementList"}],
  "YEASTS" => [{name: "theYeasts", required: true, type: 'List<YEAST>', elementtag: "@ElementList"}],
  "HOPS" => [{name: "theHops", required: true, type: 'List<HOP>', elementtag: "@ElementList"}],
  "FERMENTABLES" => [{name: "theFermentables", required: true, type: 'List<FERMENTABLE>', elementtag: "@ElementList"}],
  "HOP" => [{name: "NAME", required: true, type: 'String'},
                     {name: "VERSION", required: true, type: 'double'},
                     {name: "ALPHA", required: true, type: 'double'},
                     {name: "AMOUNT", required: true, type: 'double'},
                     {name: "USE", required: true, type: 'String', list:['Boil', 'Dry Hop', 'Mash', 'First Wort', 'Aroma']},
                     {name: "TIME", required: true, type: 'double', comment:'minutes'},
                     {name: "NOTES", required: false, type: 'String'},
                     {name: "TYPE", required: false, type: 'String', list:['Bittering', 'Aroma', 'Both']},
                     {name: "FORM", required: false, type: 'String', list:['Pellet', 'Plug', 'Leaf']},
                     {name: "BETA", required: false, type: 'double', comment:'percentage'},
                     {name: "HSI", required: false, type: 'double', comment:'percentage'},
                     {name: "ORIGIN", required: false, type: 'String'},
                     {name: "SUBSTITUTES", required: false, type: 'String'},
                     {name: "HUMULENE", required: false, type: 'double', comment:'percentage'},
                     {name: "CARYOPHYLLENE", required: false, type: 'double', comment:'percentage'},
                     {name: "COHUMULONE", required: false, type: 'double', comment:'percentage'},
                     {name: "MYRCENE", required: false, type: 'double', comment:'percentage'}],
  "FERMENTABLE" => [
                     {name: 'NAME', required: true, type: 'String'},
                     {name: 'VERSION', required: true, type: 'double'},
                     {name: 'TYPE', required: true, type: 'String', list:['Grain', 'Sugar', 'Extract', 'Dry Extract', 'Adjunct']},
                     {name: 'AMOUNT', required: true, type: 'double', comment: 'kg'},
                     {name: 'YIELD', required: true, type: 'double', comment:'percent'},
                     {name: 'COLOR', required: true, type: 'double', comment:'Lovibond'},
                     {name: 'ADD_AFTER_BOIL', required: false, type: 'String', comment:'actually bool', list:['TRUE', 'FALSE']},
                     {name: 'ORIGIN', required: false, type: 'String'},
                     {name: 'SUPPLIER', required: false, type: 'String'},
                     {name: 'NOTES', required: false, type: 'String'},
                     {name: 'COARSE_FINE_DIFF', required: false, type: 'String', comment: '(double) percent.  only really makes sense for grain or adjunct type.'},
                     {name: 'MOISTURE', required: false, type: 'String', comment: '(double) percent.  only appropriate for grain or adjunct'},
                     {name: 'DIASTIC_POWER', required: false, type: 'String', comment: '(double) only appropriate for grain or adjunct.'},
                     {name: 'PROTEIN', required: false, type: 'String', comment: '(double) percent.  only appropriate for grain or adjunct.'},
                     {name: 'MAX_IN_BATCH', required: false, type: 'double', comment: 'percent.'},
                     {name: 'RECOMMENDED_MASH', required: false, type: 'String', comment: 'really a bool.', list:['TRUE', 'FALSE']},
                     {name: 'IBU_GAL_PER_LB', required: false, type: 'double', comment: 'useful for calculating IBU'},
  ],
  "YEAST" => [
                     {name: 'NAME', required: true, type: 'String'},
                     {name: 'VERSION', required: true, type: 'double'},
                     {name: 'TYPE', required: true, type: 'String', list:['Ale', 'Lager', 'Wheat', 'Wine', 'Champagne']},
                     {name: 'FORM', required: true, type: 'String', list:['Liquid', 'Dry', 'Slant', 'Culture']},
                     {name: 'AMOUNT', required: true, type: 'double', comment:'volume or weight.  kg or l.'},
                     {name: 'AMOUNT_IS_WEIGHT', required: false, type: 'String', comment: 'really a bool.', list:['TRUE', 'FALSE']},
                     {name: 'LABORATORY', required: false, type: 'String'},
                     {name: 'PRODUCT_ID', required: false, type: 'String'},
                     {name: 'MIN_TEMPERATURE', required: false, type: 'double'},
                     {name: 'MAX_TEMPERATURE', required: false, type: 'double'},
                     {name: 'FLOCCULATION', required: false, type: 'String', list:['Low', 'Medium', 'High', 'Very High']},
                     {name: 'ATTENUATION', required: false, type: 'double', comment: 'percent'},
                     {name: 'NOTES', required: false, type: 'String'},
                     {name: 'BEST_FOR', required: false, type: 'String'},
                     {name: 'TIMES_CULTURED', required: false, type: 'double', comment: 'number of times reused'},
                     {name: 'MAX_REUSE', required: false, type: 'double'},
                     {name: 'ADD_TO_SECONDARY', required: false, type: 'String', list:['TRUE', 'FALSE'], comment:'for secondary fermentation. default FALSE.'}
  ],
  "MISC" => [
                     {name: 'NAME', required: true, type: 'String'},
                     {name: 'VERSION', required: true, type: 'double'},
                     {name: 'TYPE', required: true, type: 'String', list:['Spice', 'Fining', 'Water Agent', 'Herb', 'Flavor', 'Other']},
                     {name: 'USE', required: true, type: 'String', list:['Boil', 'Mash', 'Primary', 'Secondary', 'Bottling']},
                     {name: 'TIME', required: true, type: 'double', comment: 'minutes'},
                     {name: 'AMOUNT', required: true, type: 'double', comment: 'volume or weight.  kg or L'},
                     {name: 'AMOUNT_IS_WEIGHT', required: false, type: 'String', list:['TRUE', 'FALSE'], comment: 'really a bool.  defaults to false'},
                     {name: 'USE_FOR', required: false, type: 'String'},
                     {name: 'NOTES', required: false, type: 'String'}
  ],
  "MISC" => [
                     {name: 'NAME', required: true, type: 'String'},
                     {name: 'VERSION', required: true, type: 'double'},
                     {name: 'TYPE', required: true, type: 'String', list:['Spice', 'Fining', 'Water Agent', 'Herb', 'Flavor', 'Other']},
                     {name: 'USE', required: true, type: 'String', list:['Boil', 'Mash', 'Primary', 'Secondary', 'Bottling']},
                     {name: 'TIME', required: true, type: 'double', comment: 'minutes'},
                     {name: 'AMOUNT', required: true, type: 'double', comment: 'volume or weight.  kg or L'},
                     {name: 'AMOUNT_IS_WEIGHT', required: false, type: 'String', list:['TRUE', 'FALSE'], comment: 'really a bool.  defaults to false'},
                     {name: 'USE_FOR', required: false, type: 'String'},
                     {name: 'NOTES', required: false, type: 'String'}
  ],
  "STYLE" => [
                     {name: 'NAME', required: true, type: 'String'},
                     {name: 'VERSION', required: true, type: 'double'},
                     {name: 'CATEGORY', required: true, type: 'String'},
                     {name: 'CATEGORY_NUMBER', required: true, type: 'String'},
                     {name: 'STYLE_LETTER', required: true, type: 'String'},
                     {name: 'STYLE_GUIDE', required: true, type: 'String'},
                     {name: 'TYPE', required: true, type: 'String', list:['Lager', 'Ale', 'Mead', 'Wheat', 'Mixed', 'Cider']},
                     {name: 'OG_MIN', required: true, type: 'double'},
                     {name: 'OG_MAX', required: true, type: 'double'},
                     {name: 'FG_MIN', required: true, type: 'double'},
                     {name: 'FG_MAX', required: true, type: 'double'},
                     {name: 'IBU_MIN', required: true, type: 'double'},
                     {name: 'IBU_MAX', required: true, type: 'double'},
                     {name: 'COLOR_MIN', required: true, type: 'double'},
                     {name: 'COLOR_MAX', required: true, type: 'double'},
                     {name: 'CARB_MIN', required: false, type: 'double'},
                     {name: 'CARB_MAX', required: false, type: 'double'},
                     {name: 'ABV_MIN', required: false, type: 'double'},
                     {name: 'ABV_MAX', required: false, type: 'double'},
                     {name: 'NOTES', required: false, type: 'String'},
                     {name: 'PROFILE', required: false, type: 'String'},
                     {name: 'INGREDIENTS', required: false, type: 'String'},
                     {name: 'EXAMPLES', required: false, type: 'String'}
  ],
  "RECIPE" => [
                     {name: 'NAME', required: true, type: 'String'},
                     {name: 'VERSION', required: true, type: 'double'},
                     {name: 'TYPE', required: true, type: 'String', list:['Extract', 'Partial Mash', 'All Grain']},
                     {name: 'STYLE', required: true, type: 'STYLE'},
                     {name: 'BREWER', required: true, type: 'String'},
                     {name: 'ASST_BREWER', required: false, type: 'String'},
                     {name: 'BATCH_SIZE', required: true, type: 'double', comment:'liters'},
                     {name: 'BOIL_SIZE', required: true, type: 'double', comment:'liters'},
                     {name: 'BOIL_TIME', required: true, type: 'double', comment:'minutes'},
                     {name: 'EFFICIENCY', required: false, type: 'double', comment:'percent'},
                     {name: 'HOPS', required: true, type: 'HOPS'},
                     {name: 'FERMENTABLES', required: true, type: 'FERMENTABLES'},
                     {name: 'MISCS', required: true, type: 'MISCS'},
                     {name: 'YEASTS', required: true, type: 'YEASTS'},
                     {name: 'NOTES', required: false, type: 'String'},
                     {name: 'TASTE_NOTES', required: false, type: 'String'},
                     {name: 'TASTE_RATING', required: false, type: 'double', comment:'0-50.0'},
                     {name: 'OG', required: false, type: 'double'},
                     {name: 'FG', required: false, type: 'double'},
                     {name: 'FERMENTATION_STAGES', required: false, type: 'double'},
                     {name: 'PRIMARY_AGE', required: false, type: 'double', comment:'days'},
                     {name: 'PRIMARY_TEMP', required: false, type: 'double', comment:'C'},
                     {name: 'SECONDARY_AGE', required: false, type: 'double', comment:'days'},
                     {name: 'SECONDARY_TEMP', required: false, type: 'double', comment:'C'},
                     {name: 'TERTIARY_AGE', required: false, type: 'double', comment:'days'},
                     {name: 'TERTIARY_TEMP', required: false, type: 'double', comment:'C'},
                     {name: 'AGE', required: false, type: 'double', comment:'days'},
                     {name: 'AGE_TEMP', required: false, type: 'double', comment:'C'},
                     {name: 'DATE', required: false, type: 'String'},
                     {name: 'CARBONATION', required: false, type: 'double'},
                     {name: 'FORCED_CARBONATION', required: false, type: 'String', list:['TRUE', 'FALSE'], comment:'really a bool.  default false.'},
                     {name: 'PRIMING_SUGAR_NAME', required: false, type: 'String', comment: 'should really be required'},
                     {name: 'CARBONATION_TEMP', required: false, type: 'double', comment:'C.  should really be required'},
                     {name: 'PRIMING_SUGAR_EQUIV', required: true, type: 'double', comment: 'used for math, if you arent using corn sugar'},
                     {name: 'KEG_PRIMING_FACTOR', required: true, type: 'double', comment: 'used for math when kegging.'}
  ]
}

classes.each_key do |class_name|
  File.open("#{class_name}.java", 'w') do |f|
    is_plural = class_name[-1] == "S"

    f.puts 'package beerxml;'
    f.puts ''
    f.puts 'import java.util.List;' if is_plural
    f.puts 'import org.simpleframework.xml.ElementList;' if is_plural
    f.puts 'import org.simpleframework.xml.Element;' unless is_plural
    f.puts 'import org.simpleframework.xml.Root;'
    f.puts ''
    f.puts '@Root(strict=false)'
    f.puts "public class #{class_name} {"
    f.puts ""

    classes[class_name].each do |field|
      f.puts "  // #{field[:comment]}" if field[:comment]
      f.puts "  // list values: #{field[:list].join(', ')}" if field[:list]

      is_list = !field[:elementtag].nil?

      f.puts (is_list ? "  #{field[:elementtag]}" : "  @Element") + "(required=#{field[:required]}" + (is_list ? ", inline=true" : "") + ")"
      f.puts "  private #{field[:type]} #{field[:name]};"
      f.puts ""
    end

    f.puts "  public #{class_name}() {"
    f.puts "    super();"
    f.puts "  }"

    classes[class_name].each do |field|
      f.puts ""
      f.puts "  /**"
      f.puts "   * #{field[:comment]}" if field[:comment]
      f.puts "   * list values: #{field[:list].join(', ')}" if field[:list]
      f.puts "   */"
      f.puts "  public #{field[:type]} get#{field[:name]}() {"
      f.puts "    return #{field[:name]};"
      f.puts "  }"
    end

    f.puts "}"

  end
end
