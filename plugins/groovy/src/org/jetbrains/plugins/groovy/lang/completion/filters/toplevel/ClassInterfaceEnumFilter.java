/*
 * Copyright 2000-2007 JetBrains s.r.o.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jetbrains.plugins.groovy.lang.completion.filters.toplevel;


import org.jetbrains.annotations.NonNls;
import org.jetbrains.plugins.groovy.lang.psi.GroovyFile;
import org.jetbrains.plugins.groovy.lang.psi.api.statements.typedef.GrTypeDefinition;
import org.jetbrains.plugins.groovy.lang.psi.api.statements.expressions.GrReferenceExpression;
import org.jetbrains.plugins.groovy.lang.psi.api.statements.GrVariable;
import org.jetbrains.plugins.groovy.lang.psi.api.statements.GrVariableDeclarations;
import org.jetbrains.plugins.groovy.lang.psi.api.auxiliary.modifiers.GrModifierList;
import org.jetbrains.plugins.groovy.lang.completion.GroovyCompletionData;
import org.jetbrains.plugins.groovy.lang.completion.GroovyCompletionUtil;
import org.jetbrains.plugins.groovy.lang.parser.GroovyElementTypes;
import com.intellij.psi.filters.ElementFilter;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiErrorElement;
import com.intellij.psi.tree.TokenSet;

/**
 * @author ilyas
 */
public class ClassInterfaceEnumFilter implements ElementFilter, GroovyElementTypes {

  private static TokenSet KEYWORDS = TokenSet.create(kCLASS, kINTERFACE, kENUM);


  public boolean isAcceptable(Object element, PsiElement context) {
    if (context.getParent() != null &&
        (context.getParent() instanceof GrReferenceExpression) &&
        context.getParent().getParent() instanceof GroovyFile) {
      return true;
    }
    if (GroovyCompletionUtil.getLeafByOffset(context.getTextOffset()-1, context) != null) {
      PsiElement prev = GroovyCompletionUtil.getLeafByOffset(context.getTextOffset()-1, context);
      prev = GroovyCompletionUtil.realPrevious(prev);
      if (prev instanceof GrModifierList &&
          prev.getParent() != null &&
          prev.getParent().getParent() instanceof GroovyFile)
      return true;
    }
    return false;
  }

  public boolean isClassAcceptable(Class hintClass) {
    return true;
  }

  @NonNls
  public String toString() {
    return "'class', 'interface', 'enum' keywords filter";
  }
}
