/***
 * ASM: a very small and fast Java bytecode manipulation framework
 * Copyright (c) 2000,2002,2003 INRIA, France Telecom 
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 3. Neither the name of the copyright holders nor the names of its
 *    contributors may be used to endorse or promote products derived from
 *    this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */

package oracle.toplink.libraries.asm.tree;

import oracle.toplink.libraries.asm.CodeVisitor;
import oracle.toplink.libraries.asm.Constants;

/**
 * A node that represents an LDC instruction.
 * 
 * @author Eric Bruneton
 */

public class LdcInsnNode extends AbstractInsnNode {

  /**
   * The constant to be loaded on the stack. This parameter must be a non null
   * {@link java.lang.Integer Integer}, a {@link java.lang.Float Float}, a
   * {@link java.lang.Long Long}, a {@link java.lang.Double Double} a {@link
   * String String} or a {@link oracle.toplink.libraries.asm.Type Type}.
   */

  public Object cst;

  /**
   * Constructs a new {@link LdcInsnNode LdcInsnNode} object.
   *
   * @param cst the constant to be loaded on the stack. This parameter must be
   *      a non null {@link java.lang.Integer Integer}, a {@link java.lang.Float
   *      Float}, a {@link java.lang.Long Long}, a {@link java.lang.Double
   *      Double} or a {@link String String}.
   */

  public LdcInsnNode (final Object cst) {
    super(Constants.LDC);
    this.cst = cst;
  }

  public void accept (final CodeVisitor cv) {
    cv.visitLdcInsn(cst);
  }
}